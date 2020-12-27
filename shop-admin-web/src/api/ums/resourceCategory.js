import request from '../../utils/request';

export const listAllCate = query => {
  return request({
    url: '/fox/ums/resourceCategory',
    method: 'get',
    params: query
  });
};
export const saveOrUpdateResourceCategory = admin => {
  return request({
    url: '/fox/ums/resourceCategory',
    method: 'post',
    data:admin
  });
};
export const fetchResourceCategoryById = id => {
  return request({
    url: '/fox/ums/resourceCategory/fetchResourceCategoryById',
    method: 'get',
    params:{id:id}
  });
};
export const deleteResourceCategory = id => {
  return request({
    url: '/fox/ums/resourceCategory',
    method: 'delete',
    params:{id:id}
  });
};
export function fetchResourceSelect(){
  return request({
    url:"/fox/ums/resourceCategory/fetchResourceSelect",
    method:'get'
  })
}

