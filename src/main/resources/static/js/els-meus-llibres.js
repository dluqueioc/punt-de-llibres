new Vue({
  el: '#app',

  async created() {
    this.books = await $.get(`/api/books/user/${window.myUserId}`);
  },

  data: {
    books: []
  },

  methods: {
    filterBooks(bookStatusId) {
      return this.books.filter(book => book.bookStatusId === bookStatusId);
    },

    async changeStatus(book) {
      await $.ajax({
        url: `/api/books/${book.id}/status`,
          type: 'PUT'
      });
      book.bookStatusId = book.bookStatusId === 1 ? 2 : 1;
    },

    async remove(book) {
      await $.ajax({
        url: `/api/books/${book.id}`,
          type: 'DELETE'
      });
      this.books.splice(
        this.books.findIndex(b => b.id === book.id),
        1
      );
    }
  }
});