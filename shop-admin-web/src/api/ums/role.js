import request from '../../utils/request';

export const fetchRoleList = query => {
  return request({
    url: '/fox/ums/role',
    method: 'get',
    params: query
  });
};
export const createRole = admin => {
  return request({
    url: '/fox/ums/role',
    method: 'post',
    data:admin
  });
};
export const getRoleById = id => {
  return request({
    url: '/fox/ums/role/getRoleById',
    method: 'get',
    params:{id:id}
  });
};
export const deleteRole = id => {
  return request({
    url: '/fox/ums/role',
    method: 'delete',
    params:{id:id}
  });
};
export function updateStatus(id,status){
  return request({
    url: '/fox/ums/role/updateStatus',
    method: 'put',
    params:{
      id:id,
      status:status
    }
  });
};


