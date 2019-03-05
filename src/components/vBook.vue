<!-- 书籍索引页 -->
<template>
  <el-container>
    <div class="content">
      <div class="content_title">
         <div class="content_title_top">
           <h1>{{chapter_title}}</h1>
         </div>
         <div class="content_title_bottom">
           作者 : {{bookInfo.book_author}} | 字数 : {{bookInfo.book_fontNum}} | {{bookInfo.book_stat}} | {{bookInfo.book_type}}
         </div>
      </div>
      <div class="content_item">
          {{chapter_content}}
      </div>
      <div class="content_bottom">
        <el-button :disabled="chapter == 1" @click="previousPage">上一章</el-button>
        <el-button @click="goBookList">目录</el-button>
        <el-button :disabled="chapter == chapter_end" @click="nextPage">下一章</el-button>
      </div>
    </div>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      chapter_title:'',
      chapter_content:'',
      bookInfo:'',
      chapter:localStorage.chapter,
      chapter_end:localStorage.chapter_end
    }
  },
  methods:{
    goBookList(){
      this.$router.push({name:'bookIndex'})
    },
    toTop(){
        let currentScroll = document.documentElement.scrollTop || document.body.scrollTop;
        if (currentScroll > 0) {
           window.requestAnimationFrame(this.toTop); //递归动画
           window.scrollTo (0,currentScroll - (currentScroll/2));
        }
    },
    previousPage(){
      this.$axios.post(this.$api.getChapterContent,{
        chapter:parseInt(localStorage.chapter)-1,
        bookTitle:localStorage.bookTitle,
        token:sessionStorage.token
      }).then(res => {
        this.chapter_title = res.data.bookData.book_chapter_titile
        this.chapter_content = res.data.bookData.book_chapter_content
        this.bookInfo = res.data.bookInfo
        localStorage.chapter = parseInt(localStorage.chapter)-1
        this.chapter = localStorage.chapter
        this.toTop()
        this.chapter_end = res.data.bookData.chapter_end
      }).catch(err => {
        console.log(err);
      })
    },
    nextPage(){
      this.$axios.post(this.$api.getChapterContent,{
        chapter:parseInt(localStorage.chapter)+1,
        bookTitle:localStorage.bookTitle,
        token:sessionStorage.token
      }).then(res => {
        this.chapter_title = res.data.bookData.book_chapter_titile
        this.chapter_content = res.data.bookData.book_chapter_content
        this.bookInfo = res.data.bookInfo
        localStorage.chapter = parseInt(localStorage.chapter)+1
        this.chapter = localStorage.chapter
        this.toTop()
        this.chapter_end = res.data.bookData.chapter_end
      }).catch(err => {
        console.log(err);
      })
    }
  },
  mounted() {
     if(!localStorage.bookTitle || !localStorage.chapter){
        this.$alert('书籍信息错误','提示',{
          confirmButtonText:'确定',
          callback:() => {
            history.back()
          }
        })
    }else {
      this.$axios.post(this.$api.getChapterContent,{
          chapter:localStorage.chapter,
          bookTitle:localStorage.bookTitle,
          token:sessionStorage.token
      }).then(res=>{
          this.chapter_title = res.data.bookData.book_chapter_titile
          this.chapter_content = res.data.bookData.book_chapter_content
          this.bookInfo = res.data.bookInfo
          this.chapter_end = res.data.bookData.chapter_end
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
    }
  }
}
</script>

<style scoped>
.el-container
{
  background: #e4e3e1;
  min-height: calc(100% - 210px);
}
.content {
  width: 1000px;
  margin: 0px auto;
  margin-top: 50px;
  margin-bottom: 20px;
  background: #efedeb;
  box-shadow: 0px 1px 14px 1px #8e8e8e;
}

.content .content_title {
  width: 100%;
  height: 120px;
  border-bottom: 1px dotted #999;
  padding: 10px 120px 0 ;
}

.content .content_title .content_title_top,.content .content_title .content_title_bottom
{
  display:flex;
  justify-content: center;
  align-items: center;
}

.content .content_title .content_title_top
{
  width: 100%;
  height: 60px;
}
.content .content_title .content_title_bottom
{
  width:100%;
  height: 30px;
  color:#999;
}

.content .content_item
{
  width: 100%;
  padding: 30px;
  letter-spacing: 1px;
  text-indent: 2em;
  min-height: calc(100% - 204px);
  padding: 30px 120px 0 ;
}

.content .content_bottom
{
  width: 100%;
  display:flex;
  padding: 20px 120px;
}
.content .content_bottom .el-button
{
  flex: 1;
  font-size: 18px;
}
</style>
