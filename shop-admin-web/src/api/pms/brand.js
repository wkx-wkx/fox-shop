import request from '../../utils/request';

export const fetchBrandData = query => {
    return request({
        url: '/fox/pms/pmsBrand',
        method: 'get',
        params: query
    });
};
export const getBrandById = id => {
  return request({
    url: '/fox/pms/pmsBrand/'+id,
    method: 'get'
  });
};
/*查询品牌数据下拉框*/
export const getBrandAll = () => {
  return request({
    url: '/fox/pms/pmsBrand/all',
    method: 'get'
  });
};
export const updateFactoryStatus = params => {
  return request({
    url: '/fox/pms/pmsBrand/factory',
    method: 'put',
    params: params
  });
};
export const updateShowStatus = params => {
  return request({
    url: '/fox/pms/pmsBrand/show',
    method: 'put',
    params: params
  });
};
export function saveBrand(params) {
  return request({
    url: '/fox/pms/pmsBrand',
    method: 'post',
    params: params
  });
}
