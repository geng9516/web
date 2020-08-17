// import axios from 'axios'
axios.defaults.baseURL = 'http://localhost:6879/'
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
    Vue.prototype.$Notice.error({
      title: 'Error!',
      desc: `${error.response && error.response.data.msg}`,
      duration: 6.5
    })
    return Promise.reject(error.response)
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
        params: params
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

const post = (url, params) => {
  return new Promise(async (resolve, reject) => {
    try {
      const { data, headers  } = await axios({
        method: 'post',
        url,
        data: params
        // responseType: download ? 'blob' : 'json'
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