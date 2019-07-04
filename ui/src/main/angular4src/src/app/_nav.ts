export const navItems = [
  {
    name: 'Dashboard',
    url: '/dashboard',
    icon: 'icon-speedometer',
  },
  {
    name: 'Pipelines',
    icon: 'icon-puzzle',
    children:[
      {
        name:'view',
        url:'/previousConfig'
      },
      {
        name:'create',
        url:'/createConfig'
      }
    ]
  },
  {
    name:'Manage',
    icon:'icon-puzzle',
    children:[
      {
        name:"Applications",
        url:'/createapp'
      },{
        name: 'Releases',
        url:'/releaseConfig/release'
      }
    ]
  },

  {
    name:'Notifications',
    url:'/notificationPage',
    icon:'icon-bell'
  }
];
