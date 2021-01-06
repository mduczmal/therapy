import React from 'react';


export function getCookie(n) {
    //https://stackoverflow.com/questions/5639346/what-is-the-shortest-function-for-reading-a-cookie-by-name-in-javascript
    let c = document.cookie.match('(^|;)\\s*' + n + '\\s*=\\s*([^;]+)');
    return c ? c.pop() : '';
}

