'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  API_ROOT:'"http://localhost:8088/ecms"',
  FILE_ROOT:'"http://localhost:8000/files/logo/"'
})
