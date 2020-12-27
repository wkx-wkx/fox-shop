import request from '../../utils/request';

export const fetchMenuList = query => {
  return request({
    url: '/fox/ums/menu',
    method: 'get',
    params: query
  });
};
export const createMenu = admin => {
  return request({
    url: '/fox/ums/menu',
    method: 'post',
    data:admin
  });
};
export const getMenuById = id => {
  return request({
    url: '/fox/ums/menu/getMenuById',
    method: 'get',
    params:{id:id}
  });
};
export const deleteMenu = id => {
  return request({
    url: '/fox/ums/menu',
    method: 'delete',
    params:{id:id}
  });
};
export function updateHiddenById(id,hidden){
  return request({
    url: '/fox/ums/menu/updateHiddenById',
    method: 'put',
    params:{
      id:id,
      hidden:hidden
    }
  });
};
export function fetchMenuSelect(){
  return request({
    url:"/fox/ums/menu/fetchMenuSelect",
    method:'get'
  })
}
//分配菜单
export const fetchTreeList = query => {
  return request({
    url: '/fox/ums/menu/fetchTreeList',
    method: 'get',
    params: query
  });
};
