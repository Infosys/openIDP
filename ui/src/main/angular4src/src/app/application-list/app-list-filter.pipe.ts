import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "appListFilter",
  pure: false,
})
export class AppListFilterPipe implements PipeTransform {
  transform(items: any[], filter: string): any {
    if (!items || !filter) {
      return items;
    }
    return items.filter(
      (item) =>
        item.applicationName.toLowerCase().indexOf(filter.toLowerCase()) !== -1
    );
  }
}
