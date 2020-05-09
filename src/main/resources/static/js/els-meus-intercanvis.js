new Vue({
    el: '#app',

    data: {
        exchanges: [],
        showCompleted: true
    },

    async created() {
        this.myUserId = window.myUserId;

        try {
            this.exchanges = await $.get(`/api/exchanges/user`);
        } catch (err) {
            console.log(err);
        }
    },

    computed: {
        exchangesShown() {
            return this.showCompleted ? this.exchanges : this.exchanges.filter(exchange => exchange.statusId !== 6);
        },

        openExchanges() {
            return this.exchanges.filter((exchange) => {
                return [1, 2].includes(exchange.statusId);
            });
        },

        pendingExchanges() {
            return this.exchanges.filter((exchange) => {
                return exchange.statusId === 3;
            });
        },

        approvedExchanges() {
            return this.exchanges.filter((exchange) => {
                return exchange.statusId === 4;
            });
        },

        closedExchanges() {
            return this.exchanges.filter((exchange) => {
                return exchange.statusId === 6;
            });
        },
    },

    methods: {
        me(exchange) {
            return exchange.users.find(user => user.userId === this.myUserId);
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

        booksThatIWant(books) {
            return books.filter((book) => this.iWantTheBook(book));
        },

        booksThatTheOtherUserWants(books) {
            return books.filter((book) => !this.iWantTheBook(book));
        },

        showApproveButtons(exchange) {
            return (
                exchange.statusId === 2 &&
                this.me(exchange).approved !== null
            );
        },

        getApprovalStatus(exchange) {
            let iHaveDecided = false;
            let iApprove = false;

            const me = this.me(exchange);

            return {
                iHaveDecided: me.approved !== null,
                iApprove: me.approved
            };
        },

        iHaveClosed(exchange) {
            const me = this.me(exchange);

            return me.completed;
        },

        noOneHasClosed(exchange) {
            return ! exchange.users.find(user => user.completed);
        },

        async openChat(exchangeId) {
            const res = await $.post(`/chats/${exchangeId}`);
            console.log(res);
        },

        async postApproval(exchangeId, approve) {
            const exchange = await $.post(
                `/api/exchanges/${exchangeId}/approve?approve=${approve.toString()}`
            );
            this.updateExchange(exchange);
        },

        async postConclusion(exchangeId, close) {
            if (!close) {
                const confirmation = window.confirm("Confirmes que vols anul·lar l'intercanvi?");
                if (! confirmation) return;
            }
            const exchange = await $.post(
                `/api/exchanges/${exchangeId}/close?close=${close.toString()}`
                );
            this.updateExchange(exchange);
        },

        updateExchange(exchange) {
            const index = this.exchanges.findIndex(
                (exch) => exch.id === exchange.id
            );
            this.exchanges.splice(index, 1);
            this.exchanges.push(exchange);
        }
    },
});
