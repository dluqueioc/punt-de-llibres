new Vue({
    el: '#app',

    data: {
        messages: [],
        newMessage: '',
    },

    methods: {
        isMine(message) {
            return message.sender.id === window.myUserId;
        },

        sendMessage() {
            if (!this.newMessage) return;

            this.stompClient.send(
                `/app/chat/${window.uuid}`,
                {},
                JSON.stringify({ body: this.newMessage })
            );

            this.newMessage = '';
        },
    },

    created() {
        this.messages = JSON.parse(window.messages);

        const socket = new SockJS('/gs-guide-websocket');
        this.stompClient = Stomp.over(socket);
        this.stompClient.debug = null;
        this.stompClient.connect({}, () => {
            this.stompClient.subscribe(
                '/chat/messages/' + window.uuid,
                res => {
                    const { body, senderId } = JSON.parse(res.body);
                    this.messages.push({
                        body,
                        sender: {
                            id: senderId,
                        },
                    });
                }
            );
        });
    },
});
