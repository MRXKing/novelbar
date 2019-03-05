<!-- 书籍索引页 -->
<template>
  <el-container>
    <div class="content">
      <div class="content_item">
        <div class="content_left">
            <img v-lazy="nobook" alt="">
        </div>
        <div class="content_right">
          <h3>{{bookTitle}}</h3>
          <p><span>{{userInfo.book_author || "暂无"}}</span> |
            <span>{{userInfo.book_type || "暂无"}}</span> |
            <span>{{userInfo.book_fontNum+"字" || "暂无" }}</span> | <span>{{userInfo.book_stat || "暂无"}}</span></p>
          <p>详情</p>
          <div class="">
            <el-button type="danger" @click="goRead">开始阅读</el-button>
          </div>
        </div>
      </div>
      <div class="content_list">
          <el-tabs v-model="activeName" >
             <el-tab-pane label="目录" name="first">
               <div class="list_container">
                 <div v-for="(value,index) in bookList">
                   <p :class="index+1 <= eRead ? 'existRead' : '' " @click="goReadSe(index+1)">{{value.book_chapter_titile}}</p>
                 </div>
               </div>
             </el-tab-pane>
             <el-tab-pane label="评论" name="second">暂无评论(待开发)</el-tab-pane>
         </el-tabs>
      </div>
    </div>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      nobook: require('@/assets/nobook.png'),
      activeName:'first',
      bookTitle:'',
      userInfo:'',
      bookList:'',
      eRead:0
    }
  },
  methods:{
    goRead(){
      if (!sessionStorage.token) {
        this.$alert('请先登录', '提示', {
          confirmButtonText: '确定'
        })
      }else {
        this.$axios.get(this.$api.getUsername, {
          params: {
            token: sessionStorage.token
          }
        }).then(res => {
              this.$axios.post(this.$api.goRead,{
                  token:sessionStorage.token,
                  bookTitle:localStorage.bookTitle
              }).then(res=>{
                  localStorage.chapter = res.data.bookData.book_chapter
                  this.$router.push({name:'vBook'})
              }).catch(err => {
                if (err.response.data.errcode == 1) {
                  this.$alert('书籍信息错误', '提示', {
                    confirmButtonText: '确定',
                    callback: ()=>{
                      history.back()
                    }
                  })
                }
              })
        }).catch(err => {
          sessionStorage.removeItem('token')
          this.stat = false
          this.$notify({
           title: '请重新登录',
           message: 'token无效或过期'
         })
         setTimeout(()=>{
           location.reload()
         },500)
          console.log(err.response.data.msg.error)
        })
      }
    },
    goReadSe(chapter){
      if (!sessionStorage.token) {
        this.$alert('请先登录', '提示', {
          confirmButtonText: '确定'
        })
      }else {
        this.$axios.get(this.$api.getUsername, {
          params: {
            token: sessionStorage.token
          }
        }).then(res => {
          localStorage.chapter = chapter
          this.$router.push({name:'vBook'})
        }).catch(err => {
          sessionStorage.removeItem('token')
          this.stat = false
          this.$notify({
           title: '请重新登录',
           message: 'token无效或过期'
         })
         setTimeout(()=>{
           location.reload()
         },500)
          console.log(err.response.data.msg.error)
        })
      }
    }
  },
  mounted() {
    this.bookTitle = localStorage.bookTitle || ''
     if (!localStorage.bookTitle) {
         this.$alert('书籍信息错误', '提示', {
           confirmButtonText: '确定',
           callback: ()=>{
             history.back()
           }
         })
     }else {
       this.$axios.post(this.$api.getBookIndex,{
         bookTitle:localStorage.bookTitle,
         token:sessionStorage.token
       }).then(res => {
         this.userInfo = res.data.userInfo
         this.bookList = res.data.bookList
         this.eRead = res.data.eRead
       }).catch(err => {
         console.log(err.response);
         if (err.response.data.errcode == 1) {
           this.$alert('书籍信息错误', '提示', {
             confirmButtonText: '确定',
             callback: ()=>{
               history.back()
             }
           })
         }
       })
     }
  }
}
</script>

<style scoped>
.content {
  width: 1000px;
  margin: 0px auto;
  margin-top: 50px;
  margin-bottom: 20px;
  box-shadow: 0px 1px 14px 1px #8e8e8e;
}

.content .content_item {
  width: 100%;
  height: 200px;
  display: flex;
}

.content .content_item .content_left {
  width: 200px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.content .content_item .content_right {
  width: 800px;
  height: 100%;
  display: flex;
  flex-direction: column;
  padding: 10px;
  justify-content: space-around;
}

.content .content_item .content_right p {
  color: #797575;
}

.content  .content_list
{
  padding: 0 50px;
  width: 100%;
  margin-bottom: 30px;
}
.content  .content_list .list_container
{
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  padding:0px 10px;
}
.content  .content_list .list_container div
{
  width: 30%;
  border-bottom: 1px dotted #ccc;
  padding-bottom: 10px;
  padding-top: 10px;
  margin: 0 14px;
}
.content  .content_list .list_container div p
{
  cursor: pointer;
  width: 100%;
  white-space:nowrap;
  overflow:hidden;
  word-break:keep-all;
  text-overflow:ellipsis;
}
.content  .content_list .list_container div p:hover
{
  color:#ec4545;
}

.existRead
{
  color:#999;
}
.existRead:hover
{
  color:#999!important;
}
</style>
