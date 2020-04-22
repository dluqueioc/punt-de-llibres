new Vue({
    el: '#app',

    async created() {
        const urlParams = new URLSearchParams(window.location.search);

        if (window.loggedIn) {
            try {
                const myExchanges = await $.get('/api/exchanges/user')
                this.myRequestedBooks = myExchanges.flatMap(exch => exch.books.map(book => book.bookId));
            } catch (err) {
                console.log(err);
            }
        }

        if (urlParams.get('filter') === 'user') {
            this.filteredByUser = true;
            try {
                this.books = await $.get(
                    '/api/books/user/' + urlParams.get('q')
                );
                this.startMaterialize();
            } catch (err) {
                console.log(err);
            }
        } else {
            this.filter = urlParams.get('filter') || null;
            try {
                this.books = await $.get('/api/books');
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
        myRequestedBooks: []
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
    },

    computed: {
        showSelect() {
            const selects = ['genre', 'theme', 'language', 'publisher'];

            return selects.includes(this.filter);
        },

        booksShown() {
            return this.books
                .filter((b) => {
                    if (
                        !this.filter ||
                        (this.filter &&
                            this.showSelect &&
                            !this.selectFilterValue) ||
                        (this.filter &&
                            !this.showSelect &&
                            !this.inputFilterValue)
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
            return this.books.length
                ? `de l'usuari ${this.books[0].user.username}`
                : '';
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

        async requestBook(bookId) {
            const res = await $.post(`/api/exchanges/${this.requestedBookId}`);
            this.modal.close();
        },

        activaModal(bookId) {
            this.requestedBookId = bookId;
            this.modal.open();
        },

        tancaModal() {
            this.modal.close();
        }
    },

    mounted() {
        var elems = document.querySelectorAll('.modal');
        this.modal = M.Modal.init(elems, {})[0];
    }
});