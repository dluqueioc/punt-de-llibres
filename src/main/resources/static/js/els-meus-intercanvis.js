new Vue({
    el: '#app',

    data: {
        exchanges: [],
    },

    async created() {
        this.myUserId = window.myUserId;

        try {
            this.exchanges = await $.get(`/api/exchanges/${this.myUserId}`);
        } catch (err) {
            console.log(err);
        }
    },

    computed: {
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
                exchange.approvals.find((a) => a.userId !== this.myUserId)
            );
        },

        getApprovalStatus(exchange) {
            let iHaveDecided = false;
            let iApprove = false;

            exchange.approvals.forEach((a) => {
                if (a.userId === this.myUserId) {
                    iHaveDecided = true;
                    iApprove = a.approved;
                }
            });

            return { iHaveDecided, iApprove };
        },

        async postApproval(exchangeId, approve) {
            const exchange = await $.post(
                `/api/exchanges/${exchangeId}/approve?approve=${approve.toString()}`
            );
            this.updateExchange(exchange);
        },

        async postConclusion(exchangeId, close) {
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
