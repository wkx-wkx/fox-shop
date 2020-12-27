import request from '../../utils/request';

export const fetchCategoryData = query => {
  return request({
    url: '/fox/pms/category',
    method: 'get',
    params: query
  });
};

export function fetchParentCategoryData (){
  return request({
    url: '/fox/pms/category/parent',
    method: 'get'
  });
};
export const updateShowStatus = params => {
  return request({
    url: '/fox/pms/category/show',
    method: 'put',
    params: params
  });
};
export const updateNavStatus = params => {
  return request({
    url: '/fox/pms/category/nav',
    method: 'put',
    params: params
  });
};
export const saveCategory = params => {
  return request({
    url: '/fox/pms/category',
    method: 'post',
    params: params
  });
};
export const getCategoryById = id => {
  return request({
    url: '/fox/pms/category/'+id,
    method: 'get'
  });
};
export const getCategoryAll = () => {
  return request({
    url: '/fox/pms/category/all',
    method: 'get'
  });
};
