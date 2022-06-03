# 对接文档


## Content-Type

前端->后端:`application/x-www-form-urlencoded`

后端->前端:`application/json`

## 失败返回

```json
{
  "status": "fail",
  "msg": "...",
  "data": null
}
```

## 接口

### 登录

POST /api/login

`email=${邮箱}&password=${密码}`

```json
{
  "status": "success",
  "msg": "登录成功",
  "data": {
    "sex": "...",
    "height": "...",
    "...": "..."
  }
}
```

### 注册

POST /api/register

`email=${邮箱}&password=${密码}`

```json
{
  "status": "success",
  "msg": "注册",
  "data": {
    "sex": "...",
    "height": "...",
    "...": "..."
  }
}
```

### 修改资料

POST /api/info/update

`sex=${性别}&height=${身高}&...`

```json
{
  "status": "success",
  "msg": "修改成功",
  "data": null
}
```

### 修改头像

POST /api/avatar/upload

`byte[]`

```json
{
  "status": "success",
  "msg": "修改成功",
  "data": null
}
```