<!-- 搜索 -->
<template >
<el-container>
  <div class="content">
    <div class="content_item" v-for="(value,index) in bookdata">
      <div class="content_left">
        <img v-lazy="nobook" alt="">
      </div>
      <div class="content_right">
        <h3>{{value.book_name}}</h3>
        <p><span>{{value.book_author}}</span> |
          <span>{{value.book_type}}</span> |
          <span>{{value.book_fontNum}}字</span> |</p>
        <p>详情</p>
        <div class="">
          <span>{{value.book_stat}}</span>
          <el-button type="danger" @click="$root.goBookIndex(value.book_name)">书籍详情</el-button>
        </div>
      </div>
    </div>
    <div v-if="bookdata.length == 0" style="color:#999;text-align:center;">
      暂无内容
    </div>
    <el-pagination @current-change="current_page" :current-page.sync="currentPage" :page-size="5" layout="prev, pager, next, jumper" :total="total">
    </el-pagination>
  </div>
</el-container>
</template>


<script>
export default {
  data() {
    return {
      nobook: require('@/assets/nobook.png'),
      currentPage: 1,
      bookdata: [],
      total: 0
    }
  },
  methods: {
    current_page(currentPage) {
      this.currentPage = currentPage
      if (!this.$store.state.search) {
        this.$axios.get(this.$api.getAllBook, {
            params: {
              currentPage: this.currentPage
            }
          })
          .then(res => {
            this.bookdata = res.data.msg
            this.total = res.data.total
          }).catch(err => {
            console.log(err.response);
          })
      } else {
        this.$axios.get(this.$api.getSearchBook, {
            params: {
              currentPage: this.currentPage,
              searchContent: this.$store.state.search
            }
          })
          .then(res => {
            this.bookdata = res.data.msg
            this.total = res.data.total
          }).catch(err => {
            console.log(err.response);
          })
      }
    }
  },
  mounted() {
    if (!this.$store.state.search) {
      this.$axios.get(this.$api.getAllBook, {
          params: {
            currentPage: this.currentPage
          }
        })
        .then(res => {
          this.bookdata = res.data.msg
          this.total = res.data.total
        }).catch(err => {
          console.log(err.response);
        })
    } else {
      this.$axios.get(this.$api.getSearchBook, {
          params: {
            currentPage: this.currentPage,
            searchContent: this.$store.state.search
          }
        })
        .then(res => {
          this.bookdata = res.data.msg
          this.total = res.data.total
        }).catch(err => {
          console.log(err.response);
        })
    }
    this.$root.$on('goSearch', () => {
      this.current_page(this.currentPage)
    })
  }
}
</script>


<style scoped>
.content {
  width: 1200px;
  margin: 0px auto;
  margin-top: 50px;
  margin-bottom: 20px;
}

.content .content_item {
  width: 100%;
  height: 200px;
  display: flex;
  border-bottom: 2px solid #ccc;
}

.content .content_item:hover {
  box-shadow: 0px 1px 14px 1px #8e8e8e;
}

.content .content_item .content_left {
  width: 150px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.content .content_item .content_right {
  width: 1050px;
  height: 100%;
  display: flex;
  flex-direction: column;
  padding: 10px;
  justify-content: space-around;
}

.content .content_item .content_right p {
  color: #797575;
}

.content .content_item .content_right>div {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
