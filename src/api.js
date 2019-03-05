
let prefix = 'http://www.mrxkin.cn:3000'

export default {
  getHpBook:`${prefix}/getHpBook`,
  register:`${prefix}/register`,
  login:`${prefix}/login`,
  getUsername:`${prefix}/getUserInfo/username`, //这里包含了检测session
  logout:`${prefix}/login/logout`,
  getCompleteBook:`${prefix}/getBookInfo/complete`,
  getRecommendBook:`${prefix}/getBookInfo/recommend`,
  getAllBook:`${prefix}/getBookInfo/allBook`,
  getSearchBook:`${prefix}/getBookInfo/search`,
  getBookIndex:`${prefix}/getBookInfo/bookIndex`,
  goRead:`${prefix}/getBookInfo/goRead`,
  getChapterContent:`${prefix}/getBookInfo/getChapterContent`,
  getUserInfo:`${prefix}/getUserInfo/userInfo`,
  getUserReadBook:`${prefix}/getUserInfo/userReadBook`
}
