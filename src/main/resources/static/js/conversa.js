new Vue({
    el: '#app',

    data: {
        messages: [],
        newMessage: '',
        otherUserId: '',
        otherUserUsername: '',
        otherUserAvatar: '',
    },

    methods: {
        isMine(message) {
            return message.sender.id === window.myUserId;
        },

        isDifferentUser(index, print) {
            return this.messages[index].sender.id !== this.messages[index - 1].sender.id;
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

        scrollToLastMessage() {
            $('.messages').scrollTop($('.messages')[0].scrollHeight);
        }
    },

    created() {
        this.messages = JSON.parse(window.messages);
        this.otherUserId = window.otherUserId;
        this.otherUserUsername = window.otherUserUsername;
        this.otherUserAvatar = window.otherUserAvatar;

        const socket = new SockJS('/gs-guide-websocket');
        this.stompClient = Stomp.over(socket);
        this.stompClient.debug = null;
        this.stompClient.connect({}, () => {
            this.stompClient.subscribe(
                '/chat/messages/' + window.uuid,
                (res) => {
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

    mounted() {
        this.scrollToLastMessage();
    },

    watch: {
        messages() {
            Vue.nextTick(() => {
                this.scrollToLastMessage();
            });
        }
    }
});
