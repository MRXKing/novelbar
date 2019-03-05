<!-- 推荐 -->
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
          <span>{{value.book_fontNum}}字</span> |
        <p>详情</p>
        <div class="">
          <span>{{value.book_stat}}</span></p>
          <el-button type="danger" @click="$root.goBookIndex(value.book_name)">书籍详情</el-button>
        </div>
      </div>
    </div>
  </div>
</el-container>
</template>


<script>
export default {
  data() {
    return {
      nobook: require('@/assets/nobook.png'),
      bookdata: [],
      total:0
    }
  },
  mounted(){
    this.$axios.get(this.$api.getRecommendBook)
    .then(res => {
       this.bookdata = res.data
    }).catch(err => {
      console.log(err.response);
    })
  }
}
</script>


<style scoped>
.content {
  width: 1200px;
  margin: 0px auto;
  margin-top: 50px;
  margin-bottom: 50px;
  min-height: 3000px;
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
.content .content_item .content_right > div
{
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-pagination
{
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
