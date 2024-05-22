import axios from "@/utils/request.js";

export const addElderly = {
    add: (form) => {
        const formData = new FormData();
        formData.append('name', form.name);
        formData.append('password', form.password);
        formData.append('auth', form.auth);
        formData.append('sex', form.sex);
        formData.append('age', form.age);
        formData.append('balance', form.balance);
        formData.append('rfidtag', form.rfidtag);
        formData.append('location_id', form.location_id);
        return axios.post('/api/info/addelderly', formData,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: 5000
            });
    },

};

export const editElderly = {
    edit: (form) => {
        console.log("formlid:" + form.location_id);
        const formData = new FormData();
        formData.append('id', form.id);
        formData.append('name', form.name);
        formData.append('password', form.password);
        formData.append('auth', form.auth);
        formData.append('sex', form.sex);
        formData.append('age', form.age);
        formData.append('balance', form.balance);
        formData.append('rfidtag', form.rfidtag);
        formData.append('locationId', form.location_id);
        return axios.post('/api/info/editelderly', formData,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: 5000
            });
    },

};

export const delElderly = {
    del: (id) => {
        return axios.post('/api/info/delelderly', {
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
    addElderly,
    editElderly,
    delElderly
};