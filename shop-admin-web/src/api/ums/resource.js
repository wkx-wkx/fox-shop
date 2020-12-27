import request from '../../utils/request';

export const fetchResourceList = query => {
  return request({
    url: '/fox/ums/resource',
    method: 'get',
    params: query
  });
};
export const createResource = admin => {
  return request({
    url: '/fox/ums/resource',
    method: 'post',
    data:admin
  });
};
export const getResourceById = id => {
  return request({
    url: '/fox/ums/resource/getResourceById',
    method: 'get',
    params:{id:id}
  });
};
export const deleteResource = id => {
  return request({
    url: '/fox/ums/resource',
    method: 'delete',
    params:{id:id}
  });
};
export function fetchResourceSelect(){
  return request({
    url:"/fox/ums/resource/fetchResourceSelect",
    method:'get'
  })
}

