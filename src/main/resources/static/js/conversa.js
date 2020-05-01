new Vue({
  el: '#app',

  data: {
    messages: [],
    newMessage: ''
  },

  methods: {
    isMine(message) {
      return message.sender.id === window.myUserId;
    },

    sendMessage() {
      if (!this.newMessage) return;

      
    }
  },

  created() {
    const messages = JSON.parse(window.messages);
    messages.reverse();
    this.messages = messages;
  }
})