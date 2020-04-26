new Vue({
    el: '#app',

    data: {
        messages: [],
    },

    async created() {
        this.myUserId = window.myUserId;

        try {
            this.messages = await $.get(`/api/messages/user`);
        } catch (err) {
            console.log(err);
        }
    },

    computed: {
        openConversation() {
            return this.messages.filter((message) => {
                return message.sender === window.myUserId || message.exchange.users[0].userId === window.myUserId || message.exchange.users[1].userId === window.myUserId;
            });
        }
        
        
    },