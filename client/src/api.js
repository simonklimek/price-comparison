import axios from 'axios'
// const posts = {
//     '1': {
//       id: 1,
//       title: 'sunt aut facere',
//       body: 'quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas totam nostrum rerum est autem sunt rem eveniet architecto'
//     },
//     '2': {
//       id: 2,
//       title: 'qui est esse',
//       body: 'est rerum tempore vitae sequi sint nihil reprehenderit dolor beatae ea dolores neque fugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis qui aperiam non debitis possimus qui neque nisi nulla'
//     }
//   }

//   const comments = {
//   get: params => axios.get('/api/v2/comments', { params }),
//   delete: params => axios.delete('/api/v2/comments', { params }),
//   // etc.
// }

const posts = {
  get: params => axios.get('http://localhost:3000/cars', { params }),
  // etc.
}
  
  export function getPost (id, cb) {
    // fake an API request
    setTimeout(() => {
      if (posts[id]) {
        cb(null, posts[id])
      } else {
        cb(new Error('Post not found.'))
      }
    }, 100)
  }

// https://dev.to/kevinleedrum/axios-tips-for-real-world-apps-3bo4



export default {
  getItems: params => axios.get('http://localhost:3000/cars/', { params }),
  // comments,
  // posts
  // etc.
}

