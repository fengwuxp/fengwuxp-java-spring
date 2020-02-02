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
    const input = `${API_ADDRESS}captcha/qr_code/login`;
    return fetch(input, {
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        method: "GET"
    }).then((resp) => {
        return resp.json();
    }).catch((e) => {
        console.log("--->", e);
    });
};

const getQrCodeSate = (key) => {
    const input = `${API_ADDRESS}scan_code/state?qrCodeKey=${key}`;
    return fetch(input, {
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        method: "GET"
    });
};

const pollingQrCodeSate = (key, callback) => {
    setTimeout(() => {
        getQrCodeSate(key).then(callback)
            .catch((e) => {
                callback(e);
                pollingQrCodeSate(key, callback);
            });
    }, 1500)
};

const markQrState = (key, targetState) => {
    const input = `${API_ADDRESS}scan_code/mark_state?qrCodeKey=${key}&targetState=${targetState}`;
    return fetch(input, {
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        method: "GET"
    })/*.then((response)=>{
        console.log("--e->",response);
        response.json().then((data)=>{
            console.log("data-->",data)
        })
    }).catch((response) => {
        console.log("-->",response);
        response.json().then((data)=>{
            console.log("data-->",data)
        })

    });*/
};

const mockLogin = (qrCode) => {
    const input = `${API_ADDRESS}scan_code/login?qrCode=${qrCode}`;
    return fetch(input, {
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            "Access-Token": "hhhh"
        },
        method: "POST"
    }).then((response)=>{
        console.log("--e->",response);
        response.json().then((data)=>{
            console.log("data-->",data)
        })
    }).catch((response) => {
        console.log("-->",response);
        response.json().then((data)=>{
            console.log("data-->",data)
        })

    });
};

export const ScanLoginView = () => {

    const [captcha, setCaptcha] = useState({key: "", value: ""});
    const [captchaValue, setCaptchaValue] = useState("");
    const [qrCodeSate, setQrCodeSate] = useState("");
    useEffect(() => {
        getCaptcha().then((data: any) => {
            console.log("--->", data);
            setCaptcha(data);

            //轮询二维码状态
            pollingQrCodeSate(data.key, async (response: Response) => {
                console.log(response.headers.get('Content-Type'));
                const data: any = await response.json();
                console.log("data", data);
                const status = response.status;
                setQrCodeSate(data);
                if (status == 400) {
                    // 无效的二维码
                }
                if (status == 401) {
                    // 等待登录
                }
            })

        });
        return () => {
        };
    }, []);

    return <div>
        <div>
            <img src={`data:image/png;base64,${captcha.value}`} onClick={() => {
                getCaptcha().then((data: any) => {
                    console.log("--->", data);
                    setCaptcha(data)
                });
            }}/>
            <div>{JSON.stringify(qrCodeSate || {})}</div>
        </div>
        <button onClick={() => {

            console.log("模拟扫码");
            markQrState(captcha.key, "SCANNED");

        }}>模拟扫码
        </button>
        <button onClick={() => {

            console.log("登录");
            mockLogin(captcha.key);

        }}>确认登录
        </button>
    </div>
};
