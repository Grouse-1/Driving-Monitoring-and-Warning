import axios from "@/utils/request.js";


export const GetAdmin = {
    adminInfo: (info) => {

        return axios.get('/api/info/admin', {
            headers: {
                'Content-Type': 'application/json'
            },
            params:{
                name: info.query,
                page: info.page,
                pageSize: info.pageSize
            },
            timeout: 5000
        });
    },

};

export const GetElderly = {
    elderlyInfo: (info) => {
        return axios.get('/api/info/elderly', {
            headers: {
                'Content-Type': 'application/json'
            },
            params:{
                name: info.query,
                page: info.page,
                pageSize: info.pageSize
            },
            timeout: 5000
        });
    },

    Info(Ename) {
        return axios.get('/api/info/elderlyInfo', {
            headers: {
                'Content-Type': 'application/json'
            },
            params:{
                name: Ename.value,
            },
            timeout: 5000
        });
    }
};


export const GetSeller = {
    sellerInfo: (info) => {
        return axios.get('/api/info/seller', {
            headers: {
                'Content-Type': 'application/json'
            },
            params:{
                name: info.query,
                page: info.page,
                pageSize: info.pageSize
            },
            timeout: 5000
        });
    },

};


export const GetFamily = {
    familyInfo: (info) => {
        return axios.get('/api/info/family', {
            headers: {
                'Content-Type': 'application/json'
            },
            params:{
                name: info.query,
                page: info.page,
                pageSize: info.pageSize
            },
            timeout: 5000
        });
    },

};


export const GetRfid = {
    Info: () => {
        return axios.get('/api/info/rfidtmp', {
            headers: {
                'Content-Type': 'application/json'
            },
            timeout: 10000
        });
    },

};

export const GetNum = {
    Info: (location) => {
        return axios.get('/api/info/numbers', {
            headers: {
                'Content-Type': 'application/json'
            },
            params:{
                location: location
            },
            timeout: 5000
        });
    },

};

export const GetPlant = {
    Info: (info) => {
        return axios.get('/api/info/plant', {
            headers: {
                'Content-Type': 'application/json'
            },
            params:{
                name: info.query,
                page: info.page,
                pageSize: info.pageSize
            },
            timeout: 5000
        });
    },

};

export const GetFoods = {
    Info: (info) => {
        return axios.get('/api/info/food', {
            headers: {
                'Content-Type': 'application/json'
            },
            params:{
                name: info.value,
            },
            timeout: 5000
        });
    },

};

export const GetTrans = {
    Info: (info) => {
        return axios.get('/api/info/transcount', {
            headers: {
                'Content-Type': 'application/json'
            },
            params:{
                shopid: info,
            },
            timeout: 5000
        });
    },

};

export const GetSeven = {
    Info: (info) => {
        return axios.get('/api/info/sevenIncome', {
            headers: {
                'Content-Type': 'application/json'
            },
            params:{
                shopid: info,
            },
            timeout: 5000
        });
    },

};

export const GetBuyData = {
    Info: (info) => {
        return axios.get('/api/info/buydata', {
            headers: {
                'Content-Type': 'application/json'
            },
            params:{
                elderid: info,
            },
            timeout: 5000
        });
    },

};


export default {
    GetAdmin,
    GetElderly,
    GetSeller,
    GetFamily,
    GetRfid,
    GetNum,
    GetPlant,
    GetTrans,
    GetSeven,
    GetBuyData
};