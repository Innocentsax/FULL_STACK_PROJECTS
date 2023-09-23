import axios from "axios";

const MAKE_PAYMENT_API_BASE_URL = "http://localhost:8080/transaction/payment";
const WALLET_WITHDRAWAL_API_BASE_URL = "http://localhost:8080/transaction/transfer";
const WALLET_BALANCE_API_BASE_URL = "http://localhost:8080/transaction/walletBalance";
const WALLET_HISTORY_API_BASE_URL = "http://localhost:8080/transaction/history";
const BANK_LIST_API_BASE_URL = "http://localhost:8080/transaction/banks";

class WalletService {
    initializePaymentAndGetUrl(data, token) {
        return axios.post(MAKE_PAYMENT_API_BASE_URL, data, {
            headers: {
                Authorization: `Bearer ${token}`,// Add the Bearer token to the headers
                "Content-Type": "application/json"
            }
        });
    }

    withdrawFromWallet(data, token) {
        return axios.post(WALLET_WITHDRAWAL_API_BASE_URL, data, {
            headers: {
                Authorization: `Bearer ${token}` // Add the Bearer token to the headers
            }
        });
    }

    getWalletBalance(token) {
        return axios.get(WALLET_BALANCE_API_BASE_URL, {
            headers: {
                Authorization: `Bearer ${token}` // Add the Bearer token to the headers
            }
        });
    }

    getWalletHistory(token) {
        return axios.get(WALLET_HISTORY_API_BASE_URL, {
            headers: {
                Authorization: `Bearer ${token}` // Add the Bearer token to the headers
            }
        });
    }

    getBankList(token) {
        return axios.get(BANK_LIST_API_BASE_URL, {
            headers: {
                Authorization: `Bearer ${token}` // Add the Bearer token to the headers
            }
        });
    }


}

export default new WalletService();