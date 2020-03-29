new Vue({
  el: '#app',

  async created() {
    try {
      this.books = await $.get('/api/books');
    } catch (err) {
      console.log(err);
    }
  },

  data: {
    // loggedIn: window.loggedIn,
    loggedIn: true,
    books: [],
    filter: null,
    inputFilterValue: '',
    selectFilterValue: ''
  },

  watch: {
    filter() {
      if (! this.showSelect) {
        this.inputFilterValue = '';
      } else {
        this.selectFilterValue = this.options[0];
      }
    }
  },

  computed: {
    showSelect() {
      const selects = ['genre', 'theme', 'language', 'publisher'];

      return selects.includes(this.filter);
    },

    options() {
      const options = {
        genre: this.books.map(b => b.genre.name).filter((genre, index, self) => self.indexOf(genre) === index),
        language: this.books.map(b => b.language.name).filter((language, index, self) => self.indexOf(language) === index),
        publisher: this.books.map(b => b.publisher.name).filter((publisher, index, self) => self.indexOf(publisher) === index)
      };

      return options.hasOwnProperty(this.filter) ? options[this.filter] : [];
    },

    booksShown() {
      return this.books.filter(b => {
        if (!this.filter || (this.filter && this.showSelect && !this.selectFilterValue) || (this.filter && ! this.showSelect && ! this.inputFilterValue)) return true;

        if (this.filter === 'title') {
          return new RegExp(this.inputFilterValue, 'i').test(b[this.filter]);
        } else if (this.filter === 'author') {
          return new RegExp(this.inputFilterValue, 'i').test(b.author.name);
        } else {
          return b[this.filter].name === this.selectFilterValue;
        }
      });
    }
  }
});