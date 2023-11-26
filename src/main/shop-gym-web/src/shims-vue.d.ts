declare module '*.vue' {
  import Vue from 'vue';
  export default Vue;
}

declare module 'element-ui/lib/locale/lang/vi' {
  const ElementUILocaleEn: any;
  export = ElementUILocaleEn;
}

declare module 'element-ui/lib/locale' {
  const ElementUILocale: any;
  export = ElementUILocale;
}

