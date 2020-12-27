import request from '../../utils/request';

export const fetchAdminList = query => {
  return request({
    url: '/fox/ums/umsAdmin',
    method: 'get',
    params: query
  });
};
export const getAdminById = id => {
  return request({
    url: '/fox/ums/umsAdmin/getAdminById',
    method: 'get',
    params:{id:id}
  });
};
export const createAdmin = admin => {
  return request({
    url: '/fox/ums/umsAdmin',
    method: 'post',
    data:admin
  });
};
export const deleteAdmin = id => {
  return request({
    url: '/fox/ums/umsAdmin',
    method: 'delete',
    params:{id:id}
  });
};
export function updateStatus(id,status){
  return request({
    url: '/fox/ums/umsAdmin/updateStatus',
    method: 'put',
    params:{
      id:id,
      status:status
    }
  });
};
