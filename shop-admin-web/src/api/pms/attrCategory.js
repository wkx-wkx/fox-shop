import request from '../../utils/request';

export const fetchAttrCategoryData = query => {
    return request({
        url: '/fox/pms/attr/category',
        method: 'get',
        params: query
    });
};
/*第三个表单，属性类型下拉框*/
export const fetchAttrCategoryAll = () => {
  return request({
    url: '/fox/pms/attr/category/all',
    method: 'get'
  });
};
export const getArrtCategoryById = id => {
  return request({
    url: '/fox/pms/attr/category/'+id,
    method: 'get'
  });
};
export const saveOrUpdateCategory = params => {
  return request({
    url: '/fox/pms/attr/category/',
    method: 'post',
    params: params
  });
};
