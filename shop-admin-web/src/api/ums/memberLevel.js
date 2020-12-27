import request from '../../utils/request';

export const fetchLevelData = query => {
    return request({
        url: '/fox/ums/member/level/'+query,
        method: 'get',
    });
};
