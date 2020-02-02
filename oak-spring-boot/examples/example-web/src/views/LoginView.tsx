import React, {useEffect, useState} from "react";
import {stringify} from "querystring";

// const API_ADDRESS = "http://localhost:8090/api/"
const API_ADDRESS = process.env.API;

const loginAction = (userName, password, captcha) => {

    return fetch(`${API_ADDRESS}do_login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: stringify({
            userName,
            password,
            ...captcha
        })
    }).then((resp) => {
        return resp.json();
    }).catch((e) => {
        console.log("--->", e);
    });
};

const getCaptcha = () => {
    let input = `${API_ADDRESS}captcha/picture/login?type=ARITHMETIC`;
    return fetch(input, {
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        method: "GET"
    }).then((resp) => {
        return resp.json();
    })/*.then((response)=>{
        return response.data;
    })*/.catch((e) => {
        console.log("--->", e);
    });
};

export const LoginView = () => {

    const [captcha, setCaptcha] = useState({key: "", value: ""});
    const [captchaValue, setCaptchaValue] = useState("");
    useEffect(() => {
        getCaptcha().then((data: any) => {
            console.log("--->", data);
            setCaptcha(data)
        });
        return () => {
        };
    }, []);

    return <div>
        <div>
            <input name={"userName"} placeholder="用户名"/>
        </div>
        <div>
            <input name={"password"} placeholder="密码" type="password"/>
        </div>
        <div>
            <input name="captcha" onChange={(event) => {
                setCaptchaValue(event.target.value)
            }}/>
            <img src={`data:image/png;base64,${captcha.value}`} onClick={() => {
                getCaptcha().then((data: any) => {
                    console.log("--->", data);
                    setCaptcha(data)
                });
            }}/>
        </div>
        <button onClick={() => {

            console.log("登录");

            loginAction("admin", "123456", {
                captchaKey: captcha.key,
                captchaValue,
            }).then((resp) => {
                console.log(resp)
            }).catch(console.log);


        }}>登录
        </button>
    </div>
};
