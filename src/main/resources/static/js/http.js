const tokenKey = document.getElementsByTagName('meta')['_csrf_header'].content
const tokenValue = document.getElementsByTagName('meta')['_csrf'].content

const commRequestHeaders = {
  'X-Requested-With': 'XMLHttpRequest',
  [tokenKey]: tokenValue
}
// import axios from 'axios'
axios.defaults.baseURL = BASE_URL
axios.defaults.headers= commRequestHeaders
axios.defaults.timeout= 300 * 1000
// 请求需要携带cookie时
// axios.defaults.withCredentials = true

// http response 拦截器
axios.interceptors.response.use(
  response => {
    // 登录中是跳页应跳过
    if (response.config.url === '/login' || response.data.code === 0 || /csv|pdf|octet/g.test(response.headers['content-type'])) {
      // VUE.$Spin.hide()
      return response
    }
    return Promise.reject(response.data.msg)
  },
  error => {
    if (error.response.status === 408 || error.response.status === 403) {
      location.reload()
      return
    }
    Vue.prototype.$Notice.error({
      title: 'Error!',
      desc: error.code === 'ECONNABORTED' ? 'タイムアウトしました' : `${error.response && error.response.data.msg}`,
      duration: 6.5
    })
    return Promise.reject(error.response || error)
  }
)

const postForm = (url, params) => {
  return new Promise(async (resolve, reject) => {
    try {
      const { data } = await axios({
        method: 'post',
        url,
        data: Stringify(params, { arrayFormat: 'indices' }),
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      })
      resolve(data)
    } catch (e) {
      reject(e)
    }
  })
}

const uploadFiles = (url, formData) => {
  return new Promise(async (resolve, reject) => {
    try {
      const { data } = await axios({
        method: 'post',
        url,
        data: formData,
        transformRequest: [function (data) {
          const formData = new FormData()
          for (const key of Object.keys(data)) {
            if(data[key] instanceof Array) {
              data[key].forEach(e=>{
                formData.append(key, e, e.name)
              })
            } else {
              formData.append(key, data[key])
            }
          }
          return formData
        }],
        headers: { 'Content-Type': 'multipart/form-data' }
      })
      resolve(data)
    } catch (e) {
      reject(e)
    }
  })
}

const get = (url, params) => {
  return new Promise(async (resolve, reject) => {
    try {
      const { data, headers } = await axios({
        method: 'get',
        url,
        params: params,
        paramsSerializer: params => {
          return Stringify(params, { indices : false })
        }
      })
      if(/csv|pdf|octet/g.test(headers['content-type'])) {
        resolve({ data:data, headers: headers})
      } else {
        resolve({ ...data, headers: headers})
      }
    } catch (e) {
      reject(e)
    }
  })
}

const post = (url, params,download) => {
  return new Promise(async (resolve, reject) => {
    try {
      const { data, headers  } = await axios({
        method: 'post',
        url,
        data: params,
        responseType: download ? 'arraybuffer' : 'json'
      })
      if(/csv|pdf|octet/g.test(headers['content-type'])) {
        resolve({ data:data, headers: headers})
      } else {
        resolve({ ...data, headers: headers})
      }
    } catch (e) {
      reject(e)
    }
  })
}

const http = { get, post, postForm, uploadFiles }

Object.defineProperties(Vue.prototype, {
  http: {
    value: http,
    enumerable: true,
    configurable: true
  }
})