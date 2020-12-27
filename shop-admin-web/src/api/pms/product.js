import request from '../../utils/request';

export const fetchProductData = query => {
    return request({
        url: '/fox/pms/product/product',
        method: 'get',
        params: query
    });
};
/*提交*/
export const saveOrUpdateProductData = params => {
  return request({
    url: '/fox/pms/product/product',
    method: 'post',
    data: params
  });
};
//回显
export const getProductById = id => {
  return request({
    url: '/fox/pms/product/product/'+id,
    method: 'get'
  });
};

