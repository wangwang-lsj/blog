import request from '@/utils/request.ts';
import { serverIp } from '../../public/config.ts';

// 定义请求参数类型（示例）
interface RegisterData {
  username: string;
  password: string;
  confirmPassword: string;
}

interface LoginData {
  username: string;
  password: string;
}

interface ModifyPasswordData {
  oldPassword: string;
  newPassword: string;
}
interface QueryPageParams {
  pageNum: number;
  pageSize: number;
  username?: string;
  nickname?: string;
  adddress?: string;
  phone?: string;
  email?: string;
}
interface UserData {
  username: string;
  role: number;
  nickname?: string;
  email?: string;
  phone?: string;
  address?: string;
  avatar?: string;
}

// 用户API
const userApi = {
  /**
   * 用户注册
   * @param data
   */
  register(data: RegisterData) {
    return request({
      method: 'POST',
      url: '/api/users/register',
      data,
    });
  },

  /**
   * 用户登录
   * @param data
   */
  login(data: LoginData) {
    return request({
      method: 'POST',
      url: '/api/users/login',
      data,
    });
  },

  /**
   * 用户分页查询
   * @param params
   */
  queryPage(params: QueryPageParams) {
    return request({
      method: 'GET',
      url: '/api/users/page',
      params,
    });
  },

  /**
   * 新建用户
   * @param data
   */
  create(data: UserData) {
    return request({
      method: 'POST',
      url: '/api/users',
      data,
    });
  },

  /**
   * 修改用户
   * @param data
   */
  modify(data: UserData) {
    return request({
      method: 'PUT',
      url: '/api/users',
      data,
    });
  },

  /**
   * 删除用户通过ID
   * @param id
   */
  deleteById(id: number | string) {
    return request({
      method: 'DELETE',
      url: `/api/users/${id}`,
    });
  },

  /**
   * 批量删除用户
   * @param data
   */
  deleteBatch(data: { ids: (number | string)[] }) {
    return request({
      method: 'DELETE',
      url: '/api/users',
      data,
    });
  },

  /**
   * 修改密码
   * @param data
   */
  modifyPassword(data: ModifyPasswordData) {
    return request({
      method: 'PATCH',
      url: '/api/users',
      data,
    });
  },

  /**
   * 查询用户通过用户名
   * @param username
   */
  queryByName(username: string) {
    return request({
      method: 'GET',
      url: `/api/users/${username}`,
    });
  },

  /**
   * 导出Excel
   */
  exportExcel() {
    window.open(`http://${serverIp}:9090/api/users/export`);
  },

  /**
   * 导入Excel地址
   */
  importExcel(): string {
    return `http://${serverIp}:9090/api/users/import`;
  },
};

export default userApi;
