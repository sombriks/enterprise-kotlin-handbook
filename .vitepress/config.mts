import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  title: "Enterprise Kotlin Handbook",
  description: "My hands-on guide of how to get things done using Kotlin and the Enterprise Java ecosystem",
  ignoreDeadLinks: true,
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Get Started', link: '/README' },
    ],
    sidebar: [
      {
        text:'Get Started',
        link:'/README'
      },
      {
        text: 'Docs',
        base: '/docs',
        items: [
          { text: 'Agenda', link: '/README' },
          { text: 'Comparison with JS', link: '/0010-javascipt-kotlin-lang-comparison' },
          { text: 'The Basics', link: '/0011-kotlin-basics.md' },
          { text: 'Maven / Gradle', link: '/0012-project-setup-with-maven-and-gradle.md' },
          { text: 'Tests (Part 1)', link: '/0013-unit-tests-part-1.md' },
          { text: 'Spring', link: '/0014-spring.md' },
          { text: 'Database', link: '/0015-databases.md' },
          { text: 'Sporing + Database', link: '/0016-spring-with-databases.md' },
          { text: 'Tests (Part 2)', link: '/0017-unit-tests-part-2.md' },
          { text: 'Container Basics', link: '/0018-container-basics.md' },
          { text: 'IaC Basics', link: '/0019-iac-basics.md' },
          { text: 'CI/CD Pipelines', link: '/0020-ci-cd-pipelines.md' },
        ]
      }
    ],
    socialLinks: [
      { icon: 'github', link: 'https://github.com/sombriks/enterprise-kotlin-handbook' },
      { icon: 'bluesky', link: 'https://bsky.app/profile/sombriks.com.br' }
    ]
  }
})
