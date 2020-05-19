new Vue({
    el: '#app',

    async created() {
        const urlParams = new URLSearchParams(window.location.search);

        if (window.loggedIn) {
            try {
                const myExchanges = await $.get('/api/exchanges/user');
                this.myRequestedBooks = myExchanges.flatMap((exch) =>
                    exch.books.map((book) => book.bookId)
                );
            } catch (err) {
                console.log(err);
            }
        }

        if (urlParams.get('filter') === 'user') {
            this.filteredByUser = true;
            try {
                const books = await $.get(
                    '/api/books/user/' + urlParams.get('q')
                );
                await this.getScores(books);
                this.books = books;
                this.loading = false;
                this.startMaterialize();
            } catch (err) {
                console.log(err);
            }
        } else {
            this.filter = urlParams.get('filter') || null;
            try {
                const books = await $.get('/api/books');
                await this.getScores(books);
                this.books = books;
                this.loading = false;
                this.startMaterialize();
            } catch (err) {
                console.log(err);
            }
        }

        if (this.filter) {
            this.inputFilterValue = urlParams.get('q') || '';
            this.selectFilterValue = urlParams.get('q') || '';
        }
    },

    data: {
        loggedIn: window.loggedIn,
        books: [],
        filter: null,
        inputFilterValue: '',
        selectFilterValue: '',
        initialRender: true,
        filteredByUser: false,
        myRequestedBooks: [],
        requestingBook: false,
        radius: 1000,
        loading: true
    },

    watch: {
        filter() {
            if (this.initialRender) {
                this.initialRender = false;
                return;
            }

            if (!this.showSelect) {
                this.inputFilterValue = '';
            }
        },

        books() {
            Vue.nextTick(() => $('select').formSelect());
        }

        /*
        distancia(){
        }
        */
    },

    computed: {
        showSelect() {
            const selects = ['genre', 'theme', 'language', 'publisher'];

            return selects.includes(this.filter);
        },

        booksShown() {
            return this.books.filter((b) => {
                if (
                    !this.filter ||
                    (this.filter &&
                        this.showSelect &&
                        !this.selectFilterValue) ||
                    (this.filter && !this.showSelect && !this.inputFilterValue)
                )
                    return true;

                if (this.filter === 'title') {
                    return new RegExp(this.inputFilterValue, 'i').test(
                        b[this.filter]
                    );
                } else if (this.filter === 'author') {
                    return new RegExp(this.inputFilterValue, 'i').test(
                        b.author.name
                    );
                } else if (this.filter === 'owner') {
                    return b.userId === this.inputFilterValue;
                } else {
                    return b[this.filter].name === this.selectFilterValue;
                }
            });
        },

        fromUserText() {
            return this.books.length ? `de ${this.books[0].user.username}` : '';
        },
    },

    methods: {
        startMaterialize() {
            var vm = this;

            Vue.nextTick(() => {
                $('select').formSelect();

                $('#select-type').change(function (e) {
                    const { value } = e.target;

                    vm.filter = $(this).val();

                    if (['author', 'title'].includes(value)) {
                        $('#input-value-container').val('');
                    } else {
                        vm.selectFilterValue = $(`#select-${value}`).val();
                    }
                });

                const filters = ['genre', 'language', 'publisher', 'theme'];

                filters.forEach((filter) => {
                    $(`#select-${filter}`).change(function (e) {
                        vm.selectFilterValue = $(this).val();
                    });
                });

                $("#input-value-container, [id^='select-']").hide();

                M.AutoInit();

                //funcionalitat de cerca per distància
                $("#select-radius").change(()=> {
                	console.log("ha canviat la distancia");
                	$distancia = $("#select-radius").val();
                	console.log($distancia);
            		$.ajax({
            			url: '/api/books/geo?user=' + window.myUserId + '&radius=' + $distancia,
            			success: actualitzaBooks.bind(this),
            			dataType: "json",
            		});
                	function actualitzaBooks (resultat, statusText, jqXHR){
                		console.log(resultat);
                		this.books=resultat;
                		console.log(this.books);
                	}
                });
            });
        },

        getOptions(prop) {
            return this.books
                .map((el) => el[prop].name)
                .filter((el, index, self) => self.indexOf(el) === index)
                .sort();
        },

        options(prop) {
            const options = {
                genre: this.getOptions('genre'),
                language: this.getOptions('language'),
                publisher: this.getOptions('publisher'),
                theme: this.getOptions('theme'),
            };

            return options[prop];
        },

        requestBook(goToExchanges) {
            let exchangeId;

            try {
                this.requestingBook = true;
                setTimeout(() => {
                    $.post(`/api/exchanges/${this.requestedBookId}`).then(res => {
                        exchangeId = res;
                        if (goToExchanges) {
                            window.location = `/els-meus-intercanvis#exchange-${exchangeId}`;
                        } else {
                            this.requestingBook = false;
                            this.myRequestedBooks.push(this.requestedBookId);
                        }
                    });
                }, 700);

                if (!goToExchanges) {
                    this.modal.close();
                }
            } catch (e) {
                console.log(e);
                return;
            }
        },

        activaModal(bookId) {
            this.requestedBookId = bookId;
            this.modal.open();
        },

        tancaModal() {
            this.modal.close();
        },

        formatIsbn(isbn) {
            return isbn.replace(/[-\s]/g, '');
        },

        async getScores(books) {
            const isbns = books.reduce((aggr, book) => {
                return book.isbn
                    ? `${aggr}${aggr ? ',' : ''}${this.formatIsbn(book.isbn)}`
                    : aggr;
            }, '');

            let response;
            try {
                response = await $.get(`/api/books/scores?isbns=${isbns}`);
            } catch(e) {
                return;
            }
            const scores = JSON.parse(response).books;
            for (let score of scores) {
                const book = books.find((book) =>
                    [score.isbn, score.isbn13].includes(
                        this.formatIsbn(book.isbn)
                    )
                );
                Vue.set(book, 'goodReadsInfo', {
                    id: score.id,
                    score: score.average_rating,
                });
            }
        },
    },

    mounted() {
        var elems = document.querySelectorAll('.modal');
        this.modal = M.Modal.init(elems, {})[0];
    },
});
