# 对接文档


## Content-Type

前端->后端:`application/x-www-form-urlencoded`

后端->前端:`application/json`

## 失败返回

```json
{
  "status": "fail",
  "msg": "..."
}
```

## 接口

### 登录状态

POST /api/login/test

`None`

```json
{
  "status": "ok"
}
```


### 登录

POST /api/login

`email=${邮箱}&password=${密码}`

```json
{
  "status": "ok",
  "msg": "登录成功",
  "data": {
    "username": "",
    "sex": "",
    "height": "",
    "phone": "",
    "email": "",
    "education": "",
    "school": "",
    "address": "",
    "skills": "",
    "text": "",
  }
}
```

### 注册

POST /api/register

`email=${邮箱}&password=${密码}`

```json
{
  "status": "ok",
  "msg": "",
  "data": {
    "username": "",
    "sex": "",
    "height": "",
    "phone": "",
    "email": "",
    "education": "",
    "school": "",
    "address": "",
    "skills": "",
    "text": "",
  }
}
```

### 发送验证码

POST /api/code

`email=${邮箱}`

```json
{
  "status":"ok",
  "msg":""
}
```

### 找回密码

POST /api/retrieve

`email=${邮箱}&otp=${验证码}&password=${新密码}`

```json
{
  "status":"ok",
  "msg":""
}
```

### 修改密码

POST /api/modify

`password=${新密码}`

```json
{
  "status":"ok",
  "msg":""
}
```

### 修改资料

POST /api/info/update

`sex=${性别}&height=${身高}&...`

```json
{
  "status": "ok",
  "msg": "修改成功"
}
```

### 获取头像

POST /api/avatar/get

`None`

`image/jpeg,image/png`

### 修改头像

POST /api/avatar/upload

`image/jpeg,image/png`

```json
{
  "status": "ok",
  "msg": "修改成功",
  "data": null
}
```