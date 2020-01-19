# 使用Jekyll在本地测试GitHub Pages网站

## 安装Jekyll

https://jekyllrb.com/docs/installation/

## 安装Ruby

https://www.ruby-lang.org/en/documentation/installation/

## 安装Bundler

https://bundler.io/

## 在本地建立网站

1. 打开终端。

2. 导航到您的网站的发布源。有关发布源的更多信息，请参见“ 关于[GitHub Pages](https://help.github.com/en/github/working-with-github-pages/about-github-pages#publishing-sources-for-github-pages-sites)”。

3. 在本地运行您的Jekyll网站。

```
$ bundle exec jekyll serve
> Configuration file: /Users/octocat/my-site/_config.yml
>            Source: /Users/octocat/my-site
>       Destination: /Users/octocat/my-site/_site
> Incremental build: disabled. Enable with --incremental
>      Generating...
>                    done in 0.309 seconds.
> Auto-regeneration: enabled for '/Users/octocat/my-site'
> Configuration file: /Users/octocat/my-site/_config.yml
>    Server address: http://127.0.0.1:4000/
>  Server running... press ctrl-c to stop.
```

4. 要预览您的网站，请在网络浏览器中导航到http://localhost:4000。

# 更新GitHub Pages gem

Jekyll是一个活跃的开源项目，经常更新。
如果github-pages您计算机上的github-pagesgem与GitHub Pages服务器上的gem 已经过时，则在本地构建时与在GitHub上发布时，您的站点可能看起来有所不同。
为避免这种情况，请定期更新github-pages计算机上的gem。

1. 打开终端。

2. 更新github-pages宝石。

    如果您安装了Bundler，请运行bundle update github-pages。
    
    如果未安装Bundler，请运行gem update github-pages。