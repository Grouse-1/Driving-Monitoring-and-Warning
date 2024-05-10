import axios from "@/utils/request.js";

export const addPay = {
    Info: (form) => {
        return axios.post('/api/info/addpay', form,
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: 5000
            });
    },

};

export default {
    addPay
};