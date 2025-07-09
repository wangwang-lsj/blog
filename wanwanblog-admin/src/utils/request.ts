import axios, { AxiosError } from 'axios';
import type { AxiosInstance, InternalAxiosRequestConfig, AxiosResponse } from 'axios';
import { ElMessage } from 'element-plus';
import { serverIp } from '../../public/config';

// 添加全局未处理的 Promise rejection 监听（可选）
window.addEventListener('unhandledrejection', (event) => {
  console.error('Unhandled Rejection:', event.reason);
  event.preventDefault();
});

// 创建 axios 实例
const service: AxiosInstance = axios.create({
  baseURL: `http://${serverIp}:9090`,
  timeout: 5000,
});

// 请求拦截器
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    config.headers = config.headers || {};
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      config.headers['token'] = user.token;
    }

    return config;
  },
  (error: AxiosError) => {
    // 请求错误处理
    console.error('Request Error:', error);
    return Promise.reject(error);
  },
);

// 响应拦截器
service.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data;

    // 如果是文件流直接返回
    if (response.config.responseType === 'blob') {
      return res;
    }

    // 兼容字符串格式的响应
    if (typeof res === 'string') {
      try {
        return JSON.parse(res);
      } catch {
        return res;
      }
    }

    // 自定义错误码处理
    if (res.code === '401') {
      ElMessage.error(res.msg);
    }

    return res;
  },
  (error: AxiosError) => {
    console.error('Response Error:', error.message);

    // 可以根据 error.response.status 处理网络异常或服务器错误
    return Promise.reject(error);
  },
);

export default service;
