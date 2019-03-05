<!-- 首页笔记 -->
<template>
<el-container>
  <div class="box">
    <div class="box_top">
      <div class="left">
        <div class="item_box" v-for="(value,index) in item">
          <div class="item" v-for="(val,idx) in value" :key="idx" @click="$root.goType">
            <i class="iconfont" :class="val.icon"></i>{{val.name}}
          </div>
        </div>
      </div>
      <div class="right">
        <el-carousel trigger="click">
          <el-carousel-item v-for="(item,index) in carousel" :key="index">
            <img v-lazy="item" alt="">
          </el-carousel-item>
        </el-carousel>
        <div class="imgBox">
          <img v-lazy="item" alt="" v-for="(item,index) in pic" :key="index">
        </div>
      </div>
    </div>
    <div class="box_bottom">
      <div class="box_bottom_items" v-for="items in box">
        <div class="box_bottom_item" v-for="item in items">
            <div class="box_bottom_item_top">
              {{item[0].book_type}}
              <div class="box_bottom_item_line"></div>
            </div>
            <div class="box_bottom_item_content" v-for="value in item" >
              <ul>
                <li @click="$root.goBookIndex(value.book_name)"> <span><i class="iconfont vuejs-huangguan"></i> [{{value.book_type}}]</span> <span>{{value.book_name}}</span>
                  <span>{{value.book_author}}</span> </li>
              </ul>
            </div>

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
      item: [
        [{
            icon: 'vuejs-xuanhuan',
            name: '玄幻'
          },
          {
            icon: 'vuejs-qihuan',
            name: '奇幻'
          }
        ],
        [{
            icon: 'vuejs-wuxia',
            name: '武侠'
          },
          {
            icon: 'vuejs-xianxia',
            name: '仙侠'
          }
        ],
        [{
            icon: 'vuejs-dushi',
            name: '都市'
          },
          {
            icon: 'vuejs-xianshi',
            name: '现实'
          }
        ],
        [{
            icon: 'vuejs-junshi',
            name: '军事'
          },
          {
            icon: 'vuejs-lishi',
            name: '历史'
          }
        ],
        [{
            icon: 'vuejs-youxi',
            name: '游戏'
          },
          {
            icon: 'vuejs-tiyu',
            name: '体育'
          }
        ],
        [{
            icon: 'vuejs-kehuan',
            name: '科幻'
          },
          {
            icon: 'vuejs-lingyi',
            name: '悬疑灵异'
          }
        ],
        [{
            icon: 'vuejs-fengli',
            name: '女生网'
          },
          {
            icon: 'vuejs-erciyuan1',
            name: '二次元'
          }
        ]
      ],
      carousel: [
        require('@/assets/carousel1.jpeg'),
        require('@/assets/carousel2.jpeg'),
        require('@/assets/carousel3.jpeg')
      ],
      pic: [
        require('@/assets/pic1.jpg'),
        require('@/assets/pic2.jpg'),
        require('@/assets/pic3.jpg'),
        require('@/assets/pic4.jpg')
      ],
      box: [

      ]
    }
  },
  created(){
    this.$axios.get(this.$api.getHpBook).then(res=>{
      this.box.push(res.data)
    }).catch(err => {
      console.log(err);
    })
  }
}
</script>


<style scoped>
.box {
  width: 100%;
  min-height: 1150px;
}

.box_top {
  width: 1200px;
  display: flex;
  margin: 0 auto;
  justify-content: flex-start;
}

.el-header {
  padding: 0;
}

.box_top .left {
  width: 25%;
  min-width: 250px;
  border-bottom: 1px solid #ccc;
  border-left: 1px solid #ccc;
  border-right: 1px solid #ccc;
}

.box_top .left .item_box {
  width: 100%;
  height: 70px;
  display: flex;
  align-items: center;
}

.box_top .left .item_box:nth-child(2n+1) {
  background: #f7f6f2;
}

.box_top .left .item_box:nth-child(2n) {
  background: #fcfcfa;
}

.box_top .left .item {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

.box_top .left .item i {
  font-size: 30px;
  margin-right: 10px;
  transition: all 0.2s linear;
}

.box_top .left .item:hover {
  color: red;
}

.box_top .left .item:hover i {
  transform: translateX(-5px);
}

.box_top .right {
  width: 75%;
  height: 490px;
}

.box_top .right .el-carousel img {
  width: 100%;
  height: 100%;
}

.box_top .right .el-carousel {
  width: 100%;
  min-width: 500px;
  height: auto;
}

.box_top .right .imgBox {
  width: 100%;
  min-width: 500px;
  height: 190px;
  display: flex;
}

.box_top .right .imgBox img {
  width: 25%;
}

.box_bottom {
  width: 1200px;
  height: auto;
  margin: 0px auto;
}

.box_bottom_items {
  width: 100%;
  display: flex;
  justify-content: space-around;
  margin:50px 0;
}

.box_bottom_items .box_bottom_item {
  width: 35%;
  border: 1px solid #ccc;
  padding: 15px;
}

.box_bottom_item_top {
  width: 100%;
  text-align: center;
}

.box_bottom_item_top .box_bottom_item_line {
  width: 80%;
  height: 2px;
  margin: 0 auto;
  background: #000;
  margin-top: 20px;
}
.box_bottom_item_content
{
  width: 80%;
  margin: 0 auto;
}
.box_bottom_item_content li
{
   list-style: none;
   display: flex;
   height: 50px;
   align-items: center;
   border-bottom: 1px solid #ccc;
   cursor: pointer;
}
.box_bottom_item_content li span
{
  width: 25%;
}
.box_bottom_item_content li span:nth-child(2)
{
  width: 42%;
  text-align: center;
}
.box_bottom_item_content li span:last-child
{
  color:#ccc;
  width:33%;
  text-align: right;
}
.box_bottom_item_content li:hover ,.box_bottom_item_content li:hover *
{
  color:red!important;
}
.box_bottom_item_content li i
{
  color:#dde273;
}
.box_bottom_item_content .span
{
  word-wrap:break-word;
}

</style>
