<template>
  <div class="box">
    <div class="boxinfo">
        <h3>追书情况</h3>
        <div class="bookinfo" v-for="(value,index) in userRead">
            <div class="bookline">
                <div class="bookline_top">
                  <h2 style="cursor:pointer" @click="goBookIndex(value.book_name)">{{value.book_name}}</h2>
                </div>
                <div class="bookline_bottom">
                  <div class="bookline_bottom_left">
                    <span>{{value.book_type}}</span>
                    <span>{{value.book_stat}}</span>
                    <span>作者:{{value.book_author}}</span>
                    <span><i class="iconfont vuejs-zhangjiechuti"></i>{{value.chapter}}章</span>
                  </div>
                  <div class="bookline_bottom_right">
                    <el-button @click="continueRead(value.book_name,value.chapter)" type="text">继续阅读</el-button>
                  </div>
                </div>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
export default {
  data(){
    return {
        userRead:[]
    }
  },
  methods:{
    goBookIndex(bname){
      localStorage.bookTitle = bname
      this.$router.push({name:'bookIndex'})
    },
    continueRead(bname,chapter){
      localStorage.bookTitle = bname
      localStorage.chapter = chapter
      this.$router.push({name:'vBook'})
    }
  },
  mounted(){
    this.$axios.get(this.$api.getUsername, {
      params: {
        token: sessionStorage.token
      }
    }).then(res => {
      this.$axios.get(this.$api.getUserReadBook,{
        params:{token:sessionStorage.token}
      }).then(res => {
        console.log(res);
          this.userRead = res.data
      }).catch(err => {
        console.log(err);
      })
    }).catch(err => {
      this.$router.push({name:'novel'})
   })
  }
}
</script>

<style scoped>
.box
{
  width: 100%;
  background: #f5f6f7;
  padding: 20px 0;
}
.box .boxinfo
{
  width:1000px;
  margin:0 auto;
  padding: 0 32px 30px;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,.05);
  background: #fff;
}
.box .boxinfo h3
{
  font-size: 20px;
  color: #3d3d3d;
  height: 90px;
  line-height: 90px;
  border-bottom: 1px solid #e0e0e0;
}
.box .boxinfo .bookinfo
{
  padding-top:20px;
}
.box .boxinfo .bookinfo .bookline
{
  width: 100%;
  margin-bottom: 20px;
}
.box .boxinfo .bookinfo .bookline .bookline_top
{
  width: 100%;
}
.box .boxinfo .bookinfo .bookline .bookline_bottom
{
  width: 100%;
  display: flex;
  color: #999;
  font-size: 14px;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 10px;
  border-bottom: 1px solid #e0e0e0;
}
.box .boxinfo .bookinfo .bookline .bookline_bottom .bookline_bottom_left span
{
  margin-right: 5px;
}
</style>
