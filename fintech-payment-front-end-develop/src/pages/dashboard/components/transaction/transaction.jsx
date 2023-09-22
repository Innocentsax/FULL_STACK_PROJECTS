import React from "react";
import { BiSearch } from 'react-icons/bi'
import "./transaction.css";
import axios from "axios";
import { Component } from "react";
// const accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb3Nnb2xmQHlhaG9vLmNhIiwiZXhwIjoxNjYxODA3OTc2LCJpYXQiOjE2NjE4MDQzNzZ9.44-Zcm9rnbJiCYK50jKoT2rTcdgYyHkJIUyrs5UrPik";
const access = localStorage.getItem("token");
const apiUrl = "http://localhost:3333/api/v1";
const authAxios = axios.create({
    baseURL : apiUrl,
    headers : {
        Authorization : `Bearer ${access}`,
        "Content-Type": "*"
    }
})
class Transaction extends Component {
    constructor(){
        super();
        this.state = {
            history : [],
        };
    }
    async componentDidMount(){
        try{
            const result = await authAxios.get(`/transactionHistory`);
            const historyList = result.data.content;
            this.setState({history : historyList});
            console.log(this.state.history);
        }catch(err){
            console.log(err.message);
        };
    }
    render(){
        const classes = (money)=> money === "-" ? "money-two" : "money";
        return (<div className="transaction-container">
            <div className="search-transaction">
                <div className="search-i">
                    <span><BiSearch className="search-icon"/></span>
                </div>
                <input type="text" className="search" placeholder="Search transactions" />
            </div>
            <div className="t-history">
                <h6>Transaction history</h6>
            </div>
            <div className="list-of-history">
                {this.state.history.map(hist =>
                    <div key={hist.id} className="first-element">
                        <div className="icon">
                        </div>
                        <div className="history-name">
                            <h6 className="owner-name">{hist.name}</h6>
                            <div>
                                <h6 className="bank">{hist.bank} &nbsp; &nbsp; &nbsp; <br />{hist.transactionTime}</h6>
                            </div>
                        </div>
                        <div className="money">
                            {hist.amount}
                        </div>
                    </div>
                )}
            </div>
        </div>) }
}
export default Transaction;