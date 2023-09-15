import React, { Component } from "react";
import style from './ErrorPage.module.css'


export function ErrorPage(){

    return(
        <div className={style.container}>
            <div className={style.error}>
                <div className={style.header}>
                    <h2>404</h2>
                </div>
                <div className={style.header2}>
                    PAGE NOT FOUND!
                </div>
                <div className={style.content}>
                    <p>The page you are looking for might have been removed or had its name changed or is temporarily unavailable</p>
                </div>
                <div className={style.btn}>
                    <button>BACK TO HOME</button>
                </div>
            </div>
        </div>
    )
}

export default ErrorPage;