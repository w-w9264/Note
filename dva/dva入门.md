# DVA

[TOC]

## 安装dva-cli

## Route

在router.js文件中定义路由

```js
import React from 'react';
import { Router, Route, Switch } from 'dva/router';
import IndexPage from './routes/IndexPage/IndexPage';
import Product from './routes/ProductPage/IndexPage';	//引入要加载的页面

function RouterConfig({ history }) {
  return (
    <Router history={history}>
      <Switch>
        <Route path="/" exact component={IndexPage} />	//exact	精准匹配
        <Route path='/product' component={Product} />
      </Switch>
    </Router>
  );
}

export default RouterConfig;

```

对于路径中的#处理，可使用切换history为browserhistory

```js
import createHistory from 'history/createBrowserHistory';

const app = dva({
  history: createHistory(),
});
```

优化

```js
import { createBrowserHistory as createHistory} from 'history';

const app = dva({
  history: createHistory(),
});
```

## Component

组件的基本结构

```jsx
import React from 'react'


export default class Product extends React.Component{
    render(){
        return(
            <div>
                商品：{ this.props.title }	//{ this.props.title }可以获取调用方的传参
            </div>
        )
    }
}
```

组件使用

```js
import React from 'react'
import Product from '../../components/Product'	//引入组件


export default class IndexPage extends React.Component{
    render(){
        return(
            <div>
                <Product title='玉米'/>	//上方起的标签名直接使用
            </div>
        )
    }
}
```

## Model

处理数据和逻辑

```js
export default {

  namespace: 'example',	//全局 state 上的 key 必须添加
  state: {},	//初始值
  subscriptions: {
    setup({ dispatch, history }) {  // eslint-disable-line
    },
  },
  effects: {
    *fetch({ payload }, { call, put }) {  // eslint-disable-line
      yield put({ type: 'save' });
    },
  },
  reducers: {	//定义动作方法
    save(state, action) {
      return { ...state, ...action.payload };
    },
  },

};
```

引入model:在index.js 中

```js
app.model(require('./models/Product').default);
```

## connect

将model和component链接起来，即将组件和数据逻辑链接起来

```js
export default connect(mapStateToProps)(IndexPage);	//mapStateToProps:数据和逻辑部分即state.product，product指数据和逻辑部分的namespace;IndexPage:组件
```

```js
//路由部分
import React from 'react'
import Product from '../../components/Product'
import { connect } from 'dva'

class IndexPage extends React.Component{
    render(){
        const { productList } = this.props
        return(
            <div>
                <Product title='玉米' productList = { productList }/>
            </div>
        )
    }
}

const mapStateToProps = (state) => {
    return{
        productList:state.product
    }
}

export default connect(mapStateToProps)(IndexPage);
```

```js
//组件部分
import React from 'react'

export default class Product extends React.Component{
    render(){
        const { productList } = this.props.productList;	//定义变量将数据获取
        return(
            <div>
                商品：{ this.props.title }
                <ul>
                    {
                        productList.map((element,index) => {	//遍历数据将内容获取
                            return <li key={index}>{ element.name }</li>
                        })
                    }
                </ul>
            </div>
        )
    }
}
```

链接传递，在路由部分可以将数据逻辑与组件的链接传递到组件部分

```js
class IndexPage extends React.Component{
    render(){
        const { productList,dispatch } = this.props
        return(
            <div>
                <Product dispatch={ dispatch } title='玉米' productList = { productList }/>	//通过dispatch将dispatch传递，在组件部分可直接通过this.props.dispathc
            </div>
        )
    }
}
```

## 路由的跳转

1. Link方式

   ```js
   import { Link } from 'dva/router'	//引入Link来源与dva的router
   
   <Link to='/product'>商品详情</Link>	//to属性可以直接写路由地址
   ```

   

2. history.push

   ```js
   this.props.history.push("/product")	//通过push方法跳转，注意history是否为空，为空需要从前方调用，如路由部分传递到组件部分
   ```

   

3. withRouter

   ```
   
   ```

   

4. routerRedux