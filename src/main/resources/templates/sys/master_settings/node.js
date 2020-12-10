// 判断是否叶子节点
const isLeaf = (data, prop) => {
    return !(Array.isArray(data[prop]) && data[prop].length > 0)
  }
  
  // 创建 node 节点
   const renderNode = (h, data, context) => {
    const { props } = context
    const cls = ['org-tree-node']
    const childNodes = []
    const children = data[props.props.children]
  
    // props.collapsable可忽略，加入expand就可以默认在更新的时候，闭合树
    if (isLeaf(data, props.props.children)) {
      cls.push('is-leaf')
    } else if (props.collapsable && !data[props.props.expand]) {
      cls.push('collapsed')
    }
  
    childNodes.push(renderLabel(h, data, context))
  
    if (!props.collapsable || data[props.props.expand]) {
      childNodes.push(renderChildren(h, children, context))
    }
  
    return h(
      'div',
      {
        domProps: {
          className: cls.join(' ')
        }
      },
      childNodes
    )
  }
  
  // 创建展开折叠按钮
   const renderBtn = (h, data, context) => {
    const { props } = context
    const expandHandler = context.listeners['on-expand']
  
    let cls = ['org-tree-node-btn']
  
    if (data[props.props.expand]) {
      cls.push('expanded')
    }
  
    return h(
      'span',
      {
        class: 'org-tree-button-wrapper',
        on: {
          click: e => {
            e.stopPropagation()
            expandHandler && expandHandler(data)
          }
        }
      },
      [
        props.buttonRender
          ? props.buttonRender(h, data)
          : h('span', {
              class: cls.join(' ')
            })
      ]
    )
  }

  // 创建 label 节点
   const renderLabel = (h, data, context) => {
    const { props } = context
    const label = data[props.props.label]
    const nodeRender = props.nodeRender
    const clickHandler = context.listeners['on-node-click']
    const mousedownHandler = context.listeners['on-node-mousedown']
    const mouseupHandler = context.listeners['on-node-mouseup']
    const touchstartHandler = context.listeners['on-node-touchstart']
    const touchleaveHandler = context.listeners['on-node-touchleave']

    const childNodes = []
    if (typeof nodeRender === 'function') {
      let vnode = nodeRender(h, data)

      vnode && childNodes.push(vnode)
    } else {
      childNodes.push(label)
    }

    // 如果可以关闭并且有子节点时
    if (props.collapsable && !isLeaf(data, props.props.children)) {
      childNodes.push(renderBtn(h, data, context))
    }

    const cls = ['org-tree-node-label-inner']
    let { labelWidth, labelClassName } = props
    if (typeof labelWidth === 'number') {
      labelWidth += 'px'
    }
    if (typeof labelClassName === 'function') {
      labelClassName = labelClassName(data)
    }
    labelClassName && cls.push(labelClassName)

    return h(
      'div',
      {
        domProps: {
          className: 'org-tree-node-label'
        },
        on: {
          click: e => clickHandler && clickHandler(e, data),
          mousedown: e => mousedownHandler && mousedownHandler(e, data),
          mouseup: e => mouseupHandler && mouseupHandler(e, data),
          touchstart: e => touchstartHandler && touchstartHandler(e, data),
          touchleave: e => touchleaveHandler && touchleaveHandler(e, data)
        }
      },
      [
        h(
          'div',
          {
            domProps: {
              className: cls.join(' ')
            },
            style: { width: labelWidth }
          },
          childNodes
        )
      ]
    )
  }

  // 创建 node 子节点
   const renderChildren = (h, list, context) => {
    if (Array.isArray(list) && list.length) {
      const children = list.map(item => {
        return renderNode(h, item, context)
      })

      return h(
        'div',
        {
          domProps: {
            className: 'org-tree-node-children'
          }
        },
        children
      )
    }
    return ''
  }

   const render = (h, context) => {
    const { props } = context
    return renderNode(h, props.data, context)
  }

  const orgTree = {
    name: 'OrgTree',
    template: `<div class="org-tree" :class="{ horizontal, collapsable }">
    <org-tree-node
      :data="dataCloned"
      :props="props"
      :horizontal="horizontal"
      :label-width="labelWidth"
      :collapsable="collapsable"
      :node-render="nodeRender"
      :button-render="buttonRender"
      :label-class-name="labelClassName"
      @on-expand="handleExpand"
      @on-node-click="handleNodeClick"
      v-bind="$listeners"
    ></org-tree-node>
  </div>` ,
    data: function () {
      return {
        flatData: {},
        dataCloned: {}
      }
    },
    components: {
        OrgTreeNode: {
          render,
          functional: true
        }
      },
    props: {
        data: {
          type: [Object, Array],
          required: true
        },
        props: {
          type: Object,
          default: () => ({
            id: 'deptId',
            label: 'name',
            expand: 'expand',
            children: 'children'
          })
        },
        horizontal: Boolean,
        collapsable: Boolean,
        nodeRender: Function,
        buttonRender: Function,
        labelWidth: [String, Number],
        labelClassName: [Function, String],
        expandAll: {
          type: Boolean,
          default: false
        }
      },
      watch: {
        data: {
          handler(newData) {
            // 数据更新时，先存到dataCloned中
            this._handleData(newData)
            // 遍历每个树状节点，判断它在flatData的状态，之前有那么现在还有
            // 可以理解为数据更新后重置树的状态
            this._mapData(this.dataCloned, item => {
              const { expand } = this.flatData[item[this.prop_id]] || {}
              if (expand) this.$set(item, this.prop_expand, true)
            })
            this._toggleExpand(this.dataCloned, this.expandAll)
          },
          // immediate: true,
          deep: true
        },
        expandAll(status) {
          this._toggleExpand(this.dataCloned, status)
        }
      },
      computed: {
        prop_id() {
          return this.props.id
        },
        prop_label() {
          return this.props.label
        },
        prop_expand() {
          return this.props.expand
        },
        prop_children() {
          return this.props.children
        }
      },
      methods: {
        _handleData(data) {
          this._cloneData(data)
        },
        _cloneData(newData) {
          this.dataCloned = this.utils.deepClone(newData)
        },
        _setFlatData(data) {
          this.flatData[data[this.prop_id]] = data
        },
        /**
         * @description 工具方法，用于遍历树状数据的每个节点， fn为在该节点做的操作，其有一个参数即当前节点数据
         */
        _mapData(data, fn) {
          fn(data)
          const children = data[this.prop_children]
          if (children) {
            children.forEach(child => {
              this._mapData(child, fn)
            })
          }
        },
        /**
         * @description 用来便利所有节点数据，将树状数据扁平化存放到flatData，用于数据更新后展开状态的恢复
         */
        _updateExpandStatus() {
          this._mapData(this.dataCloned, this._setFlatData)
        },
        collapse(list) {
          let _this = this
          list.forEach(child => {
            if (child[this.prop_expand]) {
              child[this.prop_expand] = false
            }
            const children = child[this.prop_children]
            children && _this.collapse(children)
          })
        },
        handleExpand(data) {
          if (this.prop_expand in data) {
            data[this.prop_expand] = !data[this.prop_expand]
            const children = data[this.prop_children]
            if (!data[this.prop_expand] && children) {
              this.collapse(children)
            }
          } else {
            this.$set(data, this.prop_expand, true)
          }
          this.$emit('on-expand', data, data[this.prop_expand])
          this._updateExpandStatus()
        },
        _toggleExpand(data, status) {
          let _this = this
          if (Array.isArray(data)) {
            data.forEach(item => {
              _this.$set(item, this.prop_expand, status)
              const children = item[this.prop_children]
              if (children) {
                _this._toggleExpand(children, status)
              }
            })
          } else {
            _this.$set(data, this.prop_expand, status)
            const children = data[this.prop_children]
            if (children) {
              _this._toggleExpand(children, status)
            }
          }
        },
        handleNodeClick(e, data) {
          this.$emit('on-node-click', e, data, () => {
            console.log('xxx')
            // this.handleExpand(data)
          })
        },
        toggleExpand() {
          this._toggleExpand(this.dataCloned, this.expandAll)
          this._updateExpandStatus()
        }
      },
      mounted() {
        this._handleData(this.data)
        this._updateExpandStatus()
        this._toggleExpand(this.dataCloned, this.expandAll)
      }
  }