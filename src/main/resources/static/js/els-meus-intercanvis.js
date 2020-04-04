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
                return exchange.statusId === 1;
            });
        },

        pendingExchanges() {
            return this.exchanges.filter((exchange) => {
                return exchange.statusId === 2;
            });
        }
    },

    methods: {
        exchangeStarter(book, exchange) {
            return this.iWantTheBook(book) ?
                "mi" :
                this.getOtherUser(exchange).user.username;
        },

        iWantTheBook(book) {
            return book.userId == this.myUserId;
        },

        getOtherUser(exchange) {
            return exchange.users.find(u => u.userId != this.myUserId);
        },
    }
});
