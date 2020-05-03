new Vue({
    el: '#app',

    data: {
        chats: [],
    },

    methods: {
        otherUser(chatIndex) {
            const chat = this.chats[chatIndex];
            const prop = chat.user1Id === window.myUserId ? 2 : 1;
            return chat[`user${prop}`];
        },

        lastMessageBody(chatIndex) {
            const message = this.chats[chatIndex].messages.slice(-1)[0];
            return `${message.body}`;
        },

        lastMessageInfo(chatIndex) {
            const message = this.chats[chatIndex].messages.slice(-1)[0];
            const date = message.date;
            return `${message.sender.username}, ${moment(date).from(moment())}`;
        },

        goToChat(chatId) {
            location.href = `/conversa/${chatId}`;
        },
    },

    created() {
        let chats = JSON.parse(window.myChats);

        chats = chats.map((chat) => ({
            ...chat,
            messages: chat.messages.map((message) => ({
                ...message,
                date: new Date(
                    message.createdDate.year,
                    message.createdDate.monthValue - 1,
                    message.createdDate.dayOfMonth,
                    message.createdDate.hour,
                    message.createdDate.minute,
                    message.createdDate.second
                ),
            })),
        }));

        chats.sort((a, b) => {
            let aDate = a.messages.slice(-1)[0].date;
            let bDate = b.messages.slice(-1)[0].date;
            return bDate - aDate;
        });

        this.chats = chats;
    },
});
