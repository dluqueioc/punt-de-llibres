new Vue({
    el: '#app',

    data: {
        exchanges: [],
        showCompleted: true,
        modal: null,
        selectedExchangeId: false,
    },

    async created() {
        this.myUserId = window.myUserId;

        try {
            this.exchanges = await $.get(`/api/exchanges/user`);
            this.exchanges.reverse();
        } catch (err) {
            console.log(err);
        }
    },

    computed: {
        exchangesShown() {
            return this.showCompleted
                ? this.exchanges
                : this.exchanges.filter((exchange) => exchange.statusId !== 6);
        },

        closedExchanges() {
            return this.exchanges.filter((exchange) => {
                return exchange.statusId === 6;
            });
        },
    },

    methods: {
        me(exchange) {
            return exchange.users.find((user) => user.userId === this.myUserId);
        },

        startedByMe({ starterUserId }) {
            return starterUserId === this.myUserId;
        },

        showUserBooks(exchange) {
            const { userId } = this.getOtherUser(exchange);
            window.location = `/llibres-disponibles?filter=user&q=${userId}`;
        },

        iWantTheBook(book) {
            return book.userId == this.myUserId;
        },

        getOtherUser(exchange) {
            return exchange.users.find((u) => u.userId != this.myUserId);
        },

        getOtherUserLink(exchange) {
            const userId = this.getOtherUser(exchange).user.id;
            return `/llibres-disponibles?filter=user&q=${userId}`;
        },

        booksThatIWant(books) {
            return books.filter((book) => this.iWantTheBook(book));
        },

        booksThatTheOtherUserWants(books) {
            return books.filter((book) => !this.iWantTheBook(book));
        },

        getApprovalStatus(exchange) {
            let iHaveDecided = false;
            let iApprove = false;

            const me = this.me(exchange);

            return {
                iHaveDecided: me.approved !== null,
                iApprove: me.approved,
            };
        },

        iHaveClosed(exchange) {
            const me = this.me(exchange);

            return me.completed;
        },

        noOneHasClosed(exchange) {
            return !exchange.users.find((user) => user.completed);
        },

        async openChat(exchangeId) {
            const chatId = await $.post(`/chats?exchangeId=${exchangeId}`);
            window.location = `/conversa/${chatId}`;
        },

        postApproval(exchangeId, approve) {
            if (!approve) {
                this.selectedExchangeId = exchangeId;
                this.modal.open();
            } else {
                this.approve(exchangeId, true);
            }
        },

        async approve(exchangeId, approve) {
            const exchange = await $.post(
                `/api/exchanges/${exchangeId}/approve?approve=${approve.toString()}`
            );
            this.updateExchange(exchange);
        },

        postConclusion(exchangeId, close) {
            if (!close) {
                this.selectedExchangeId = exchangeId;
                this.modal.open();
            } else {
                this.close(exchangeId, true);
            }
        },

        async close(exchangeId, close) {
            const exchange = await $.post(
                `/api/exchanges/${exchangeId}/close?close=${close.toString()}`
            );
            this.updateExchange(exchange);
        },

        updateExchange(exchange) {
            const index = this.exchanges.findIndex(
                (exch) => exch.id === exchange.id
            );
            if (exchange.statusId === 5) {
                this.exchanges.splice(index, 1);
            } else {
                this.exchanges.splice(index, 1, exchange);
            }
        },
    },

    mounted() {
        var elems = document.querySelectorAll('.modal');
        this.modal = M.Modal.init(elems, {})[0];

        let interval = setInterval(goToHash, 200);

        function goToHash() {
            const hash = window.location.hash;
            if (hash) {
                const elem = document.getElementById(hash.substring(1));
                if (elem) {
                    elem.scrollIntoView();
                    clearInterval(interval);
                }
            }
        }
    },
});
