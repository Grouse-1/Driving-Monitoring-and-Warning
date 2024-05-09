import axios from "@/utils/request.js";

export const addSeller = {
    add: (form) => {
        const formData = new FormData();
        formData.append('name', form.name);
        formData.append('password', form.password);
        formData.append('auth', form.auth);
        formData.append('shopname', form.shopname);
        formData.append('phone', form.phone);
        return axios.post('/api/info/addseller', formData,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: 5000
            });
    },

};

export const editSeller = {
    edit: (form) => {
        const formData = new FormData();
        formData.append('id', form.id);
        formData.append('name', form.name);
        formData.append('password', form.password);
        formData.append('auth', form.auth);
        formData.append('shopname', form.shopname);
        formData.append('phone', form.phone);
        return axios.post('/api/info/editseller', formData,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: 5000
            });
    },

};

export const delSeller = {
    del: (id) => {
        return axios.post('/api/info/delseller', {
                id: id,
            },
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: 5000
            });
    },

};

export default {
    addSeller,
    editSeller,
    delSeller
};