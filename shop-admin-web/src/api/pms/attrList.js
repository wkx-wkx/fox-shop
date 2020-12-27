import request from '../../utils/request';

export const fetchAttrListData = query => {
    return request({
        url: '/fox/pms/attr/attribute',
        method: 'get',
        params: query
    });
};
export const getAttrById = id => {
  return request({
    url: '/fox/pms/attr/attribute/'+id,
    method: 'get'
  });
};
export const saveOrUpdateAttr = params => {
  return request({
    url: '/fox/pms/attr/attribute/',
    method: 'post',
    params: params
  });
};
